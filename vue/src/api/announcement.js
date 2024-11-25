import request from '@/utils/request.js';

export const getAllAnnouncements = () => {
  return request.get('/home/admin/announcements');
};

export const getAnnouncements = () => {
  return request.get('/home/announcements');
};

export const addAnnouncement = (announcement) => {
  return request.post('/home/admin/announcements', announcement);
};

export const updateAnnouncement = (announcement) => {
  return request.put(`/home/admin/announcements/${announcement.id}`, announcement);
};

export const deleteAnnouncement = (id) => {
  return request.delete(`/home/admin/announcements/${id}`);
}; 