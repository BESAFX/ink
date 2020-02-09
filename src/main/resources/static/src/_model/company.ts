import { Person } from './person';
export class Company {
    private _id?: number;
    private _name?: string;
    private _address?: string;
    private _mobile?: string;
    private _phone?: string;
    private _fax?: string;
    private _email?: string;
    private _website?: string;
    private _commericalRegisteration?: string;
    private _licenceCode?: string;
    private _taxCode?: string;
    private _options?: string;
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
     * Getter address
     * @return {string}
     */
    public get address(): string {
        return this._address;
    }

    /**
     * Getter mobile
     * @return {string}
     */
    public get mobile(): string {
        return this._mobile;
    }

    /**
     * Getter phone
     * @return {string}
     */
    public get phone(): string {
        return this._phone;
    }

    /**
     * Getter fax
     * @return {string}
     */
    public get fax(): string {
        return this._fax;
    }

    /**
     * Getter email
     * @return {string}
     */
    public get email(): string {
        return this._email;
    }

    /**
     * Getter website
     * @return {string}
     */
    public get website(): string {
        return this._website;
    }

    /**
     * Getter commericalRegisteration
     * @return {string}
     */
    public get commericalRegisteration(): string {
        return this._commericalRegisteration;
    }

    /**
     * Getter licenceCode
     * @return {string}
     */
    public get licenceCode(): string {
        return this._licenceCode;
    }

    /**
     * Getter taxCode
     * @return {string}
     */
    public get taxCode(): string {
        return this._taxCode;
    }

    /**
     * Getter options
     * @return {string}
     */
    public get options(): string {
        return this._options;
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
     * Setter address
     * @param {string} value
     */
    public set address(value: string) {
        this._address = value;
    }

    /**
     * Setter mobile
     * @param {string} value
     */
    public set mobile(value: string) {
        this._mobile = value;
    }

    /**
     * Setter phone
     * @param {string} value
     */
    public set phone(value: string) {
        this._phone = value;
    }

    /**
     * Setter fax
     * @param {string} value
     */
    public set fax(value: string) {
        this._fax = value;
    }

    /**
     * Setter email
     * @param {string} value
     */
    public set email(value: string) {
        this._email = value;
    }

    /**
     * Setter website
     * @param {string} value
     */
    public set website(value: string) {
        this._website = value;
    }

    /**
     * Setter commericalRegisteration
     * @param {string} value
     */
    public set commericalRegisteration(value: string) {
        this._commericalRegisteration = value;
    }

    /**
     * Setter licenceCode
     * @param {string} value
     */
    public set licenceCode(value: string) {
        this._licenceCode = value;
    }

    /**
     * Setter taxCode
     * @param {string} value
     */
    public set taxCode(value: string) {
        this._taxCode = value;
    }

    /**
     * Setter options
     * @param {string} value
     */
    public set options(value: string) {
        this._options = value;
    }

    /**
     * Setter persons
     * @param {Person[]} value
     */
    public set persons(value: Person[]) {
        this._persons = value;
    }

}


