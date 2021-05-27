import Time from "./time";

export default interface Team {
    name: string;
    members: string[];
    pointChanges: Map<Date, number>;
    timeToday: Time;
    timeTotal: Time;
    pointsTotal: number;
    pointsToday: number;
}

export function createDefaultTeam(): Team {
    return {
        name: '',
        members: [],
        pointChanges: new Map(),
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
        },
        pointsTotal: 0,
        pointsToday: 0
    }
}