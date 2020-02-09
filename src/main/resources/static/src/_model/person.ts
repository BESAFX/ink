import { Contact } from './contact';
export class Person {
    private _id?: number;
    private _email?: string;
    private _options?: string;
    private _contact?: Contact;

    /**
    * Getter id
    * @return {number}
    */
    public get id(): number {
        return this._id;
    }

    /**
     * Getter email
     * @return {string}
     */
    public get email(): string {
        return this._email;
    }

    /**
     * Getter options
     * @return {string}
     */
    public get options(): string {
        return this._options;
    }

    /**
     * Getter contact
     * @return {Contact }
     */
    public get contact(): Contact {
        return this._contact;
    }

    /**
     * Setter id
     * @param {number} value
     */
    public set id(value: number) {
        this._id = value;
    }

    /**
     * Setter email
     * @param {string} value
     */
    public set email(value: string) {
        this._email = value;
    }

    /**
     * Setter options
     * @param {string} value
     */
    public set options(value: string) {
        this._options = value;
    }

    /**
     * Setter contact
     * @param {Contact } value
     */
    public set contact(value: Contact) {
        this._contact = value;
    }

}

