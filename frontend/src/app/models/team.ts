import Time from "./time";

export default interface Team {
    name: string;
    members: string[];
    timeChanges: Map<Date, number>;
    timeToday: Time;
    timeTotal: Time;
}

export function createDefaultTeam(): Team {
    return {
        name: '',
        members: [],
        timeChanges: new Map(),
        timeToday: {
            hours: 0,
            minutes: 0,
            seconds: 0,
            timeInSeconds: 0
        },
        timeTotal: {
            hours: 0,
            minutes: 0,
            seconds: 0,
            timeInSeconds: 0
        }
    }
}