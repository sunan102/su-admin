import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/admin/company/getList',
    method: 'get',
    params
  })
}

export function save(data) {
  return request({
    url: '/admin/company/save',
    method: 'post',
    data
  })
}

export function getDetail(id) {
  return request({
    url: '/admin/company/detail',
    method: 'get',
    params: {
      id: id
    }
  })
}

export function disable(id) {
  return request({
    url: '/admin/company/disable',
    method: 'post',
    params:{
      id:id
    }
  })
}

export function enable(id) {
  return request({
    url: '/admin/company/enable',
    method: 'post',
    params:{
      id:id
    }
  })
}

export function getAllCompany(params) {
  return request({
    url: '/admin/company/getAllCompany',
    method: 'get',
    params
  })
}
