import { Person } from './person';
export class Team {
    private _id?: number;
    private _name?: string;
    private _authorities?: string;
    private _persons?: Person[];

    /**
    * Getter id
    * @return {number}
    */
    public get id(): number {
        return this._id;
    }

    /**
     * Getter name
     * @return {string}
     */
    public get name(): string {
        return this._name;
    }

    /**
     * Getter authorities
     * @return {string}
     */
    public get authorities(): string {
        return this._authorities;
    }

    /**
     * Getter persons
     * @return {Person[]}
     */
    public get persons(): Person[] {
        return this._persons;
    }

    /**
     * Setter id
     * @param {number} value
     */
    public set id(value: number) {
        this._id = value;
    }

    /**
     * Setter name
     * @param {string} value
     */
    public set name(value: string) {
        this._name = value;
    }

    /**
     * Setter authorities
     * @param {string} value
     */
    public set authorities(value: string) {
        this._authorities = value;
    }

    /**
     * Setter persons
     * @param {Person[]} value
     */
    public set persons(value: Person[]) {
        this._persons = value;
    }

}
