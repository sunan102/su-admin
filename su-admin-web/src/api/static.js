import request from '@/utils/request'

export function getAdminUserStatus() {
  return request({
    url: '/static_data/getAdminUserStatus',
    method: 'get'
  })
}