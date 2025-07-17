import axios from 'axios';

const getCurrentUser = async () => {
  const response = await axios.get('/api/v1/users/me');
  return response.data;
};

const updateEmail = async (newEmail: string) => {
  const response = await axios.put('/api/v1/users/update-email', { email: newEmail });
  return response.data;
};

const updatePassword = async (newPassword: string) => {
  const response = await axios.put('/api/v1/users/update-password', { password: newPassword });
  return response.data;
};

export default {
  getCurrentUser,
  updateEmail,
  updatePassword,
};
