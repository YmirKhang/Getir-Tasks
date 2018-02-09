const express = require('express');
const router = express.Router();
const mongoose = require("mongoose");

const Record = require('../models/record');

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

router.post('/',(req, res, next) =>{
    res.status(201).json({
        message: 'First post request'
    });
});

module.exports = router;
