export class RecipeModel {
    id?: number
    userId?: number;
    name: string;
    level: string;
    time: string;
    portions: string;
    ingredients?: Array<string>;
    steps?: Array<string>;
    description?: string;
}
