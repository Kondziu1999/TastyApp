import { User } from './user';
export class UserDto{
    name: string;
    username: string;
    password :string;  
    email: string;

    constructor(user: User){
        this.name=user.firstName+" "+user.lastName;
        this.username=user.username;
        this.password=user.password;
        this.email=user.email;
    }
}