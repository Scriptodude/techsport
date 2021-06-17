import Translation from "./translation";

export default class English implements Translation {
    strava = {
        title: "Ask for approbation via Strava",
        openingFormat: (start, end) => `Open from ${start} to ${end}, Montreal's time`,
        upToDatePosition: "Make sure your position is up to date",
        onYourProfile: "on your strava profile",
        clickOnTheButtonBelow: "By clicking on the button below, you will be able to import all activities since your last import",
        explanation: "This will create activities to be approved by the admins.",
        import: "Import my activities",
        connect: "Connect with strava",
        myActivities: "My Activities",
        linkToStravaActivity: "Link to the activity (Strava)",
        noActivityTitle: "You have no activity",
        importClosed: "The import is currently closed"
    };
    activityCard = {
        athlete: "Athlete",
        approved: "Approved for",
        toValidate: "To Validate",
        declined: "Declined",
        manual: "Activity Created Manually !",
        unsupported: "Unsupported type"
    };
    navigation = {
        welcome: "Home",
        points: "Scoreboard",
        teams: "Teams",
        admin: "Admin"
    };
    timeSeparator = '\\a\\t';
    scoreboard = {
        titleNow: "Current Score",
        titleDone: "Final Score",
        position: "Rank",
        teamName: "Team name",
        pointsToday: "Points today",
        pointsBySport: "Points by sport",
        walkHike: "Walk / Hike",
        bike: "Bike",
        run: "Running",
    };
}