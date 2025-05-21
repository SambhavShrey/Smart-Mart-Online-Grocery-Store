import { User } from "./user.model";

export interface Feedback{
    feedbackId?:number;
    user?:User;
    userId?:number
    message:string;
    rating:number;
}