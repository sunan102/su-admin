import request from '@/utils/request'

export function login(username, password) {
    return request({
        url: '/oauth/token',
        method: 'get',
        params: {
            username,
            password,
            grant_type: "password",
            client_id:"client_2",
            client_secret:"secret"
        }
    })
}

export function getInfo() {
    return request({
        url: 'admin/user/info',
        method: 'get'
    })
}

export function logout() {
    return request({
        url: '/oauth/logout',
        method: 'post'
    })
}

export function getMenuList() {
    return request({
        url: 'admin/user/getMenuList',
        method: 'get'
    })
}
