export default interface Translation {
    strava: Strava
    activityCard: ActivityCard
    timeSeparator: string
    navigation: Navigation
    scoreboard: Scoreboard
}

interface Strava {
    title: string,
    openingFormat: (start, end) => string,
    upToDatePosition: string,
    onYourProfile: string,
    clickOnTheButtonBelow: string,
    explanation: string,
    import: string,
    connect: string,
    myActivities: string,
    linkToStravaActivity: string,
    noActivityTitle: string,
    importClosed: string,
    notLoggedIn: string
}

interface ActivityCard {
    athlete: string,
    approved: string,
    toValidate: string,
    declined: string,
    manual: string,
    unsupported: string
}

interface Navigation {
    welcome: string,
    points: string,
    teams: string,
    admin: string
}

interface Scoreboard {
    titleNow: string,
    titleDone: string,
    position: string,
    teamName: string,
    pointsToday: string,
    pointsBySport: string,
    walkHike: string,
    bike: string,
    run: string,
}