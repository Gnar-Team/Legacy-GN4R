package com.gmail.hexragon.gn4rBot.command.misc;


import java.util.Random;

public class GnarQuotes {

    private static String[] quotes =
            {
                    "Gnar gada!",
                    "Gnar!",
                    "Shubbanuffa.",
                    "Vimaga.",
                    "Nakotak.",
                    "Kshaa!",
                    "Vigishu!",
                    "Wap!",
                    "Hwa!",
                    "Vrooboo.",
                    "Raag!",
                    "Wabbo!",
                    "Gnar squeals.",
                    "Mega Gnar roars.",
                    "Mega Gnar roars.",
                    "Gnar.",
                    "Maga.",
                    "Shagdovala!",
                    "Hursh, rao!",
                    "Ovagarava!",
                    "Onna legga.",
                    "Okalannomaka.",
                    "Ahanga!",
                    "Oga lagga.",
                    "Goova.",
                    "Oga manni maxa.",
                    "Reeshoova!",
                    "Fue huega.",
                    "Okanoo.",
                    "Ganaloo mo.",
                    "Gnar groans.",
                    "Gnar yelps.",
                    "Gnar scoffs.",
                    "Gnar sniffs.",
                    "Mega Gnar snarls.",
                    "Mega Gnar snarls.",
                    "Mega Gnar snarls.",
                    "Mega Gnar snarls.",
                    "Shoo shoo? Bahnah!",
                    "Mega Gnar begins to roar, but chokes and coughs.",
                    "Gnar chants.",
                    "Gnar chants.",
                    "Mega Gnar chants.",
                    "Shugi shugi shugi!",
                    "Haygo vaygo!",
                    "Jay Watford.",
                    "Tibbahs!",
                    "Shargh!",
                    "Demaglio!",
                    "Marmess!",
                    "Mo'kay.",
                    "Mega Gnar roars.",
                    "Mega Gnar roars.",
                    "Gnar laughs.",
                    "Gnar laughs.",
                    "Gnar laughs.",
                    "Gnar laughs.",
                    "Mega Gnar chuckles.",
                    "GNAR!"
            };

    public static String getRandomQuote() {

        Random random = new Random();
        int randomNumber = random.nextInt(quotes.length);

        return quotes[randomNumber];
    }
    
}
