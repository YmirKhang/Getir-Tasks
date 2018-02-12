const express = require('express');
const router = express.Router();
const mongoose = require("mongoose");

const Record = require('../models/record');

const { check, validationResult } = require('express-validator/check');
const { matchedData, sanitize } = require('express-validator/filter');

router.get('/',(req, res, next) =>{
    return res.status(200).json({
      msg: "Make a post request"
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
