import { Cabinet } from "./Cabinet";
import { Role } from "./enums";

export class Crew{
    public id!: number;
    public nom!: string;
    public prenom!: string;
    public email!: string;
    public pwd!: string;
    public role!: Role;
    public locked!: boolean;
    public cabinet!: Cabinet;
}