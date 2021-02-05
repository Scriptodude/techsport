import Time from "./time";

export interface TimeLog {
    whoEntered: string;
    teamName: string;
    why: string;
    athleteName: string;
    dateTime: string;
    time: Time
}

export interface TimeLogResponse{
    logs: TimeLog[];
    pages: number;
}
