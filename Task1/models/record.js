const mongoose = require('mongoose');

const recordSchema = mongoose.Schema({
  _id: mongoose.Schema.Types.ObjectId,
  key: String,
  value: String,
  createdAt: Date,
  counts: [Number]
});

module.exports = mongoose.model('Record',recordSchema)
