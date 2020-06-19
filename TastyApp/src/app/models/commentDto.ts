export class commentDto{

    constructor(comment:string, userId: number = 0, date: Date = new Date(), username?: string){
        this.comment = comment;
        this.date = date;
        this. userId = userId;
        this.username = username;
    }

    public comment?: string;
    public  date: Date | undefined;
    //username is optional
    public  userId: number;
    public  username: string | undefined;
}