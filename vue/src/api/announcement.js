import request from '@/utils/request.js';

export const getAnnouncements = () => {
  return request.get('/api/announcements');
}; 