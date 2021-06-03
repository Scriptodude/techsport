import { stringify } from "@angular/compiler/src/util";
import Time from "./time";

export interface Activity {
    id: string;
    athleteFullName: string;
    activityTime: Time;
    activityDate: string;
    distance: number | null;
    type: string | null;
    manual: Boolean | null;
    points: number | null;
    appliedRate: number | null;
    approved: Boolean | null;
    teamName: string | null;
}

export default interface ActivityResponse {
    activities: Activity[];
    pages: number;
}

export function createDefaultActivity() {
    return {
        id: '',
        athleteFullName: '',
        activityTime: {
            hours: 0,
            minutes: 0,
            seconds: 0,
            timeInSeconds: 0
        },
        activityDate: '',
        distance: null,
        type: null,
        manual: null,
        points: null,
        appliedRate: null,
        approved: null,
        teamName: null
    }
}