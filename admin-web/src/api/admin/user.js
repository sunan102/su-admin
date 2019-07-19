import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/admin/user/getList',
    method: 'get',
    params
  })
}

export function save(data) {
  return request({
    url: '/admin/user/save',
    method: 'post',
    data
  })
}

export function getDetail(id) {
  return request({
    url: '/admin/user/getDetail',
    method: 'get',
    params: {
      id: id
    }
  })
}

export function disable(id) {
  return request({
    url: '/admin/user/disable',
    method: 'post',
    params:{
      id:id
    }
  })
}

export function enable(id) {
  return request({
    url: '/admin/user/enable',
    method: 'post',
    params:{
      id:id
    }
  })
}

export function resetPwd(data) {
  return request({
    url: '/admin/user/resetPwd',
    method: 'post',
    data
  })
}