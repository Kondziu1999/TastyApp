

export class ResetPaswordPayload{
    usernameOrEmail?: string;
    password?: string;
    token?: string;
    frontendUrl?: string;

    constructor(usernameOrEmail: string,
        password: string,
        token: string,
        frontendUrl: string){
            if(usernameOrEmail) this.usernameOrEmail=usernameOrEmail;
            if(password) this.password=password;
            if(token) this.token=token;
            if(frontendUrl) this.frontendUrl=frontendUrl;
        }
}