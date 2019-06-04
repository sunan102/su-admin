import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/admin/menu/getList',
    method: 'get',
    params
  })
}

export function save(data) {
  return request({
    url: '/admin/menu/save',
    method: 'post',
    data
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
