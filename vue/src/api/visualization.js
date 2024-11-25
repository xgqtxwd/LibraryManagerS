import request from '@/utils/request.js';

export const getPopularBooks = () => {
  return request.get('/visualization/popular-books');
};

export const getTopUsers = () => {
  return request.get('/visualization/top-users');
}; 