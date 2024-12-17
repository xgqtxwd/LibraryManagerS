import request from '@/utils/request.js';

export const getOverdueRecords = () => {
  return request.get('/home/admin/overdue');
};
export const getOverdueRecordsByUserId = () => {
  return request.get('/home/admin/personal-overdue');
};
export const sendReminder = (reminderData) => {
  return request.post('/home/admin/send-reminder', reminderData);
};
