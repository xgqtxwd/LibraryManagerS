import {useTokenStore} from "@/store/token.js";
import request from "@/utils/request.js";

export  const  categoryListService=()=>{
    // const tokenStore = useTokenStore();

    // return request.get("/category",{headers:{Authorization:tokenStore.token}});
    return request.get("/category")
}
export const  categoryDeleteService=(id)=>{
    return request.delete("/category/delete?id="+id)
}
export const categoryAddService=(categoryDate)=>{
    return request.post("/category/insert",categoryDate)
}
export const categoryUpdateService=(categoryDate)=>{
    return request.put("/category/update",categoryDate)
}
