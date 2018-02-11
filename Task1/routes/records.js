const express = require('express');
const router = express.Router();
const mongoose = require("mongoose");

const Record = require('../models/record');

const { check, validationResult } = require('express-validator/check');
const { matchedData, sanitize } = require('express-validator/filter');

router.get("/", (req, res, next) => {
  const reducer = (accumulator, currentValue) => accumulator + currentValue;
  Record.find() //TODO aggregation function for the total count and post request response
    .select("key createdAt counts")
    .exec()
    .then(docs => {
      res.status(200).json({
        code: 0,
        msg: "Success",
        //count: docs.length,
        records: docs.map(doc => {
          return {
            key: doc.key,
            createdAt: doc.createdAt,
            totalCount: doc.counts.reduce(reducer),

          /*  request: {
              type: "GET",
              url: "http://localhost:3000/searchRecords/" + doc._id
            }*/
          };
        })
      });
    })
    .catch(err => {
      res.status(500).json({
        code: 1,
        msg: "Fail",
        error: err
      });
    });
});

router.get("/test", (req, res, next) => {
  let startDate = new Date(new Date("2016-01-26").toISOString());
  let inputDate = new Date(startDate.toISOString());
  Record
    .aggregate([{
     $project: {
       _id: 0,
       createdAt:1,
       key:1,
       totalCount: { $sum: "$counts"},
     }
   },{$match:{ $and: [{ totalCount: { $gte: 2000 , $lte:3000 }},{ createdAt: { $gte: startDate } }]}}
    ])
    .exec()
    .then(docs => {
      res.status(200).json({
        code: 0,
        msg: "Success",
        records: docs.map(doc => {
          return {
            key: doc.key,
            createdAt: doc.createdAt,
            totalCount: doc.totalCount,
          };
        })
      });
    })
    .catch(err => {
      res.status(500).json({
        code: 1,
        msg: "Fail",
        error: err
      });
    });
});

router.post('/',[
    check('startDate').exists(),
    check('endDate').exists(),
    check('minCount').exists(),
    check('maxCount').exists()
],(req, res, next) =>{

  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(422).json(
      {code: 1,
      msg: "Fail",
      errors: errors.mapped() });
  }

  Record
    .aggregate([{
     $project: {
       _id: 0,
       createdAt:1,
       key:1,
       totalCount: { $sum: "$counts"},
     }
   },{$match:{ $and: [
     { totalCount: { $gte: req.body.minCount , $lte: req.body.maxCount }},
     { createdAt: { $gte: new Date(new Date(req.body.startDate).toISOString()), $lte: new Date(new Date(req.body.endDate).toISOString()) } }]}}
    ])
    .exec()
    .then(docs => {
      res.status(200).json({
        code: 0,
        msg: "Success",
        records: docs.map(doc => {
          return {
            key: doc.key,
            createdAt: doc.createdAt,
            totalCount: doc.totalCount,
          };
        })
      });
    })
    .catch(err => {
      res.status(500).json({
        code: 1,
        msg: "Fail",
        error: err
      });
    });

});

module.exports = router;
