export class CredentailsAvailability {
    usernameAvailable : boolean;
    emailAvailable: boolean;

    constructor(usernameAvailable: boolean,emailAvailable:boolean){
        this.usernameAvailable=usernameAvailable;
        this.emailAvailable=emailAvailable;
    }
}
