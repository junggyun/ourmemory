import store from "@/store";
import {AxiosInstance, InternalAxiosRequestConfig} from "axios";


const setInterceptors = function (instance : AxiosInstance) {
    instance.interceptors.request.use(
        (config: InternalAxiosRequestConfig) => {
            const token = store.state.token
            config.headers.Authorization = `Bearer ${token}`
            return config
        },
        (error) => {
            return Promise.reject(error)
        }
    )

    instance.interceptors.response.use(
        (response) => {
            return response
        },
        (error) => {
            return Promise.reject(error)
        }
    )
    return instance
}

export { setInterceptors }
