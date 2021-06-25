package com.aqualen.xml.filter;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.sink.SinkRecord;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class XmlFilterTest {

    @Test
    void checkFilter() {
        String value = "<?xml version=\"1.0\"?><people><name>Luke Skywalker</name><height>172</height><mass>77</mass><hair_color>blond</hair_color><skin_color>fair</skin_color><eye_color>blue</eye_color><birth_year>19BBY</birth_year><gender>male</gender><homeworld>http://swapi.co/api/planets/1/</homeworld><films>http://swapi.co/api/films/6/</films><films>http://swapi.co/api/films/3/</films><films>http://swapi.co/api/films/2/</films><films>http://swapi.co/api/films/1/</films><films>http://swapi.co/api/films/7/</films><species>http://swapi.co/api/species/1/</species><vehicles>http://swapi.co/api/vehicles/14/</vehicles><vehicles>http://swapi.co/api/vehicles/30/</vehicles><starships>http://swapi.co/api/starships/12/</starships><starships>http://swapi.co/api/starships/22/</starships><created>2014-12-09T13:50:51.644000Z</created><edited>2014-12-20T21:17:56.891000Z</edited><url>http://swapi.co/api/people/1/</url><desc>Luke Skywalker is a fictional character and the main protagonist of the original film trilogy of the Star Wars franchise created by George Lucas. The character, portrayed by Mark Hamill, is an important figure in the Rebel Alliance's struggle against the Galactic Empire. He is the twin brother of Rebellion leader Princess Leia Organa of Alderaan, a friend and brother-in-law of smuggler Han Solo, an apprentice to Jedi Masters Obi-Wan \"Ben\" Kenobi and Yoda, the son of fallen Jedi Anakin Skywalker (Darth Vader) and Queen of Naboo/Republic Senator Padmé Amidala and maternal uncle of Kylo Ren / Ben Solo. The now non-canon Star Wars expanded universe depicts him as a powerful Jedi Master, husband of Mara Jade, the father of Ben Skywalker and maternal uncle of Jaina, Jacen and Anakin Solo.</desc><desc>In 2015, the character was selected by Empire magazine as the 50th greatest movie character of all time.[2] On their list of the 100 Greatest Fictional Characters, Fandomania.com ranked the character at number 14.[3]</desc></people>";
        XmlFilter<SinkRecord> sinkRecordXmlFilter = new XmlFilter<>();
        sinkRecordXmlFilter.configure(new HashMap<String,String>(){{put("x-path", "/people[name='Luke Skywalker']");}});
        SinkRecord record = new SinkRecord("test", 1, Schema.STRING_SCHEMA, "test",
                Schema.STRING_SCHEMA, value, 1);
        SinkRecord result = sinkRecordXmlFilter.apply(record);

        assertNotNull(result);
    }

    @Test
    void checkThatFilterExcluded() {
        String value = "<?xml version=\"1.0\"?><people><name>C-3PO</name><height>167</height><mass>75</mass><skin_color>gold</skin_color><eye_color>yellow</eye_color><birth_year>112BBY</birth_year><homeworld>http://swapi.co/api/planets/1/</homeworld><films>http://swapi.co/api/films/5/</films><films>http://swapi.co/api/films/4/</films><films>http://swapi.co/api/films/6/</films><films>http://swapi.co/api/films/3/</films><films>http://swapi.co/api/films/2/</films><films>http://swapi.co/api/films/1/</films><species>http://swapi.co/api/species/2/</species><created>2014-12-10T15:10:51.357000Z</created><edited>2014-12-20T21:17:50.309000Z</edited><url>http://swapi.co/api/people/2/</url><desc>C-3PO (/siːˈθriːpi.oʊ/) or See-Threepio is a humanoid robot character from the Star Wars franchise who appears in the original trilogy, the prequel trilogy and the sequel trilogy. Built by Anakin Skywalker, C-3PO was designed as a protocol droid intended to assist in etiquette, customs, and translation, boasting that he is \"fluent in over six million forms of communication\". Along with his astromech droid counterpart and friend R2-D2, C-3PO provides comic relief within the narrative structure of the films, and serves as a foil. Anthony Daniels has portrayed the character in all nine Star Wars cinematic films released to date, including Rogue One and the animated The Clone Wars; C-3PO and R2-D2 are the only characters to appear in every film.</desc><desc>Despite his oblivious nature, C-3PO has played a pivotal role in the Galaxy's history, appearing under the service of Shmi Skywalker, the Lars homestead, Padmé Amidala, Raymus Antilles, Luke Skywalker, and Leia Organa. In the majority of depictions, C-3PO's physical appearance is primarily a polished gold plating, although his appearance varies throughout the films; including the absence of metal coverings in The Phantom Menace, a dull copper plating in Attack of the Clones, a silver lower right leg introduced in A New Hope, and a red left arm in The Force Awakens.[1] C-3PO also appears frequently in the Star Wars Canon and Star Wars Legends continuities of novels, comic books, and video games, and was the protagonist in the ABC television show Droids.</desc></people>\n";
        XmlFilter<SinkRecord> sinkRecordXmlFilter = new XmlFilter<>();
        sinkRecordXmlFilter.configure(new HashMap<String,String>(){{put("x-path", "/people[name='Luke Skywalker']");}});
        SinkRecord record = new SinkRecord("test", 1, Schema.STRING_SCHEMA, "test",
                Schema.STRING_SCHEMA, value, 1);
        SinkRecord result = sinkRecordXmlFilter.apply(record);

        assertNull(result);
    }

}