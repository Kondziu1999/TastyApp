export class RecipeModel {
    recipeId?: number
    userId?: number;
    name: string;
    level: string;
    time: string;
    portions: string;
    ingredients?: Array<string>;
    steps?: Array<string>;
    description?: string;
}
