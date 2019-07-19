import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/admin/role/getList',
    method: 'get',
    params
  })
}

export function save(data) {
  return request({
    url: '/admin/role/save',
    method: 'post',
    data
  })
}

export function getDetail(id) {
  return request({
    url: '/admin/role/getDetail',
    method: 'get',
    params: {
      id: id
    }
  })
}

export function del(id) {
  return request({
    url: '/admin/role/delete',
    method: 'post',
    params:{
      id:id
    }
  })
}

export function getAllRole(params) {
  return request({
    url: '/admin/role/getAllRole',
    method: 'get',
    params
  })
}