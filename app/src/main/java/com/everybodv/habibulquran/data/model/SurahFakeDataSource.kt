package com.everybodv.habibulquran.data.model

object SurahFakeDataSource {

    private const val makiyyah = "Makiyyah"
    private const val madaniyah = "Madaniyah"

    val surah = listOf(
        Quran("Al-Fatihah", 7, makiyyah, listOf(
            SurahAyat(1, "بِسْمِ اللّٰهِ الرَّحْمٰنِ الرَّحِيْمِ", "Bismillāhir-raḥmānir-raḥīm(i).", "Dengan nama Allah Yang Maha Pengasih lagi Maha Penyayang."),
            SurahAyat(2, "اَلْحَمْدُ لِلّٰهِ رَبِّ الْعٰلَمِيْنَِۙ", "Al-ḥamdu lillāhi rabbil-‘ālamīn(a).", "Segala puji bagi Allah, Tuhan semesta alam."),
            SurahAyat(3, "الرَّحْمٰنِ الرَّحِيْمِِۙ", "Ar-raḥmānir-raḥīm(i).", "Yang Maha Pengasih lagi Maha Penyayang,"),
            SurahAyat(4, "مٰلِكِ يَوْمِ الدِّيْنِۗ", "Māliki yaumid-dīn(i).", "Pemilik hari Pembalasan."),
            SurahAyat(5, "اِيَّاكَ نَعْبُدُ وَاِيَّاكَ نَسْتَعِيْنُِۗ", "Iyyāka na‘budu wa iyyāka nasta‘īn(u),", "Hanya kepada Engkaulah kami menyembah dan hanya kepada Engkaulah kami memohon pertolongan."),
            SurahAyat(6, "اِهْدِنَا الصِّرَاطَ الْمُسْتَقِيْمَِۙ", "Ihdinaṣ-ṣirāṭal-mustaqīm(a).", "Bimbinglah kami ke jalan yang lurus,"),
            SurahAyat(7, "صِرَاطَ الَّذِيْنَ اَنْعَمْتَ عَلَيْهِمْ ەۙ غَيْرِ الْمَغْضُوْبِ عَلَيْهِمْ وَلَا الضَّاۤلِّيْنَِ", "Ṣirāṭal-lażīna an‘amta ‘alaihim, gairil-magḍūbi ‘alaihim wa laḍ-ḍāllīn(a).", "(yaitu) jalan orang-orang yang telah Engkau beri nikmat, bukan (jalan) mereka yang dimurkai dan bukan (pula jalan) orang-orang yang sesat."))
        ),
        Quran("Al-Ikhlas", 4, makiyyah, listOf(
            SurahAyat(1, "قُلْ هُوَ اللّٰهُ اَحَدٌِۚ", "Qul huwallāhu aḥad(un).", "Katakanlah (Nabi Muhammad), “Dialah Allah Yang Maha Esa.\n"),
            SurahAyat(2, "اَللّٰهُ الصَّمَدَُِۚۙ", "Allāhuṣ-ṣamad(u).", "Allah tempat meminta segala sesuatu."),
            SurahAyat(3, "لَمْ يَلِدْ وَلَمْ يُوْلَدِِْۙۙ", "Lam yalid wa lam yūlad.", "Dia tidak beranak dan tidak pula diperanakkan"),
            SurahAyat(4, "وَلَمْ يَكُنْ لَّهٗ كُفُوًا اَحَدٌِۗ", "Wa lam yakul lahū kufuwan aḥad(un).", "serta tidak ada sesuatu pun yang setara dengan-Nya."))
        ),
        Quran("Al-Kausar", 3, makiyyah, listOf(
            SurahAyat(1, "اِنَّآ اَعْطَيْنٰكَ الْكَوْثَرَِۗ", "Innā a‘ṭainākal-kauṡar(a).", "Sesungguhnya Kami telah memberimu (Nabi Muhammad) nikmat yang banyak."),
            SurahAyat(2, "فَصَلِّ لِرَبِّكَ وَانْحَرْۗ", "Faṣalli lirabbika wanḥar.", "Maka, laksanakanlah salat karena Tuhanmu dan berkurbanlah!"),
            SurahAyat(3, "اِنَّ شَانِئَكَ هُوَ الْاَبْتَرُِِۙ", "Inna syāni'aka huwal-abtar(u).", "Sesungguhnya orang yang membencimu, dialah yang terputus (dari rahmat Allah)."))
        )
    )
}