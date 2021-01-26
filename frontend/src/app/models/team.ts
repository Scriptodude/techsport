import Time from "./time";

export default interface Team {
    name: string;
    members: string[];
    timeChanges: Map<Date, number>;
    timeToday: Time;
    timeTotal: Time;
}