const express = require('express');
const router = express.Router();
const mongoose = require("mongoose");

const Record = require('../models/record');

router.get("/", (req, res, next) => {
  Record.find() //TODO aggregation function for the total count and post request response
    .select("record key createdAt")
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
