import request from '../utils/request.js'

export function bookCategoryListService() {
  return request.get('/category')
}

export const bookListService = (parms) => {
  return request.get('/book/borrow',{params:parms})
}
export const bookBorrowService = (id) => {
  return request.get('/book/borrowed?id='+id)
}

export  const bookUpdateService = (bookDate) => {
  return request.put('/book/update',bookDate)
}

export const bookAddService = (book) => {
  return request.post('/book/add', book);
}


export const bookReturnListService = (parms) => {
  return request.get('/book/still',{params:parms})
}
export const bookReturnService = (id) => {
  return request.get('/book/return?id='+id)
}
export const borrowAgainService = (id) => {
  return request.get('/book/again?id='+id)
}