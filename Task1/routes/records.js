const express = require('express');
const router = express.Router();
const mongoose = require("mongoose");

const Record = require('../models/record');

router.get("/", (req, res, next) => {
  Record.find()
    .select("record key _id")
    .exec()
    .then(docs => {
      res.status(200).json({
        count: docs.length,
        records: docs.map(doc => {
          return {
            _id: doc._id,
            key: doc.key,
            
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
