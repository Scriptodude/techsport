export default interface Team {
    name: string;
    members: string[];
    timeChanges: Map<Date, number>;
    hours: number;
    minutes: number;
    seconds: number;
}