import English from "./english";
import French from "./french";
import Translation from "./translation";

export default class TranslatedComponent {
    public translation: Translation = new French()

    init() {
        const locale = sessionStorage.getItem('locale')
        console.log(locale);
        if (locale == 'en') {
            this.translation = new English()
        } else {
            this.translation = new French()
        }
    }
}