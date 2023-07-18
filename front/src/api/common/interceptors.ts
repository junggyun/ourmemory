import store from "@/store";
import axios, {AxiosInstance, InternalAxiosRequestConfig} from "axios";
import {refreshAPI} from "@/api";
import jwtDecode from "jwt-decode";
import router from "@/router";

interface MyPayload {
    sub: String
    auth: String
    exp: number
}

const setInterceptors = function (instance: AxiosInstance) {
    instance.interceptors.request.use(
        async (config: InternalAxiosRequestConfig) => {
            const token = store.state.token
            const currentTime = Math.floor(Date.now() / 1000);
            if (store.state.tokenExp <= currentTime) {
                const refreshTokenRequest = {
                    refreshToken: store.getters.getRefreshToken
                }
                try {
                    const result = await refreshAPI(store.state.userId, refreshTokenRequest);
                    const newToken = result.data.accessToken;
                    const refreshToken = result.data.refreshToken;
                    const tokenExp = jwtDecode<MyPayload>(newToken).exp;
                    const refreshTokenExp = jwtDecode<MyPayload>(refreshToken).exp;
                    store.commit('setToken', newToken);
                    store.commit('setTokenExp', tokenExp);
                    store.commit('setRefreshToken', refreshToken);
                    store.commit('setRefreshTokenExp', refreshTokenExp);
                    config.headers.Authorization = `Bearer ${newToken}`;
                } catch (error: any) {
                    if (error.response.status == 401) {
                        await router.replace("/")
                    }
                }
            } else {
                config.headers.Authorization = `Bearer ${token}`
            }
            // config.headers.Authorization = `Bearer ${token}`

            return config;
        },
        (error) => {
            return Promise.reject(error)
        }
    )

    instance.interceptors.response.use(
        (response) => {
            return response
        },
        async (error) => {
            // const {
            //     config,
            //     response: { status },
            // } = error;
            // if (error.response.status === 403) {
            //     const originalRequest = config;
            //     const refreshTokenRequest = {
            //         refreshToken: store.state.refreshToken
            //     }
            //     const result = await refreshAPI(store.state.userId, refreshTokenRequest);
            //     const newToken = result.data.accessToken;
            //     const refreshToken = result.data.refreshToken;
            //     const tokenExp = jwtDecode<MyPayload>(newToken).exp;
            //     const refreshTokenExp = jwtDecode<MyPayload>(refreshToken).exp;
            //     store.commit('setToken', newToken);
            //     store.commit('setTokenExp', tokenExp);
            //     store.commit('setRefreshToken', refreshToken);
            //     store.commit('setRefreshTokenExp', refreshTokenExp);
            //     config.headers.Authorization = `Bearer ${newToken}`;
            //
            //     return axios(originalRequest);
            // }
            //
            // console.log("response")
            return Promise.reject(error);
        }
    )
    return instance
}


export {setInterceptors}
