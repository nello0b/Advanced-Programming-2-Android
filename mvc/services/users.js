const User = require('../models/userPassName');

const createUser = async (username, password, displayName, profilePic) => {
  var flag = await isUserExist(username);
  if (!flag) {
    const newUser = new User({
      username: username, 
      password: password, 
      displayName: displayName, 
      profilePic: profilePic
    });
    return await newUser.save();
  } else {
    return null;
  } 
}

const getUserByUsername = async (username) => {
  const flag = await isUserExist(username);
  if (flag) {
    return await User.findOne({username: username});
  } else {
    return null;
  }
}
 

const isUserExist = async (username) => {
  const user = await User.findOne({username: username});
  return user !== null;
}

module.exports = { createUser, getUserByUsername };