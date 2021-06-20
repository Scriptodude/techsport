export interface TeamStatistics {
    teamName: string,
    totalActivities: number,
    statsPerType: Map<string, ActivityTypeStatistics>
    totalAthletes: number
};

export interface ActivityTypeStatistics {
    count: number,
    distance: CommonStatistics,
    time: CommonStatistics,
    points: CommonStatistics
}

interface CommonStatistics {
    min: number,
    max: number,
    average: number,
    total: number
}