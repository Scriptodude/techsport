import Translation from "./translation";

export default class French implements Translation {
    strava = {
        title: "Demande d'approbation via strava",
        openingFormat: (start, end) => `Ouvert du ${start} au ${end}, heures de Montréal`,
        upToDatePosition: "Assurez-vous que votre position est à jour",
        onYourProfile: "sur votre profile strava",
        clickOnTheButtonBelow: "En cliquant sur le bouton ci-dessous, vous allez pouvoir importer toutes vos activités publiques depuis votre dernier import",
        explanation: "Ceci ajoutera alors des demandes à être approuvés par les admins.",
        import: "Importer mes activitées",
        connect: "Me connecter avec Strava",
        myActivities: "Mes activitées",
        linkToStravaActivity: "Lien vers l'activité (Strava)",
        noActivityTitle: "Vous n'avez aucune activitée",
        importClosed: "L'import est présentement fermé !",
        notLoggedIn: "Vous n'êtes pas connectés à Strava"
    };
    activityCard = {
        athlete: "Athlète",
        approved: "Approuvée pour l'équipe",
        toValidate: "À Valider",
        declined: "Refusée",
        manual: "Activité Manuelle !",
        unsupported: "Type non supporté"
    };
    navigation = {
        welcome: "Accueil",
        points: "Pointage",
        teams: "Équipes",
        admin: "Administrer"
    };
    timeSeparator = 'à'
    scoreboard = {
        titleNow: "Pointage Actuel",
        titleDone: "Pointage Final",
        position: "Position",
        teamName: "Nom de l'équipe",
        pointsToday: "Points aujourd'hui",
        pointsBySport: "Points par sport",
        walkHike: "Marche / Randonnée",
        bike: "Vélo",
        run: "Course",
    }
}