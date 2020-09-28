package ttl.larku.app;

import ttl.larku.domain.Track;
import ttl.larku.service.TrackService;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlaylistApp {


    public static void main(String[] args) {
        //new PlaylistApp().go();
        new PlaylistApp().collectorsFlatmappingInJdk8();
    }

    public void go() {
        TrackService ts = new TrackService();
        initService(ts);
        List<Track> playList = ts.getAllTracks();

        //playList.forEach(System.out::println);

        //Artists for Tracks with duration greater than 500
        List<String> artists = playList.stream().filter(t -> {
            if (t.getDuration() == null) {
                return false;
            }
            int duration = Integer.valueOf(t.getDuration().replace(":", ""));
            return duration < 500;
        }).flatMap(track -> track.getArtists().stream())
                .collect(Collectors.toList());

        //Partition of Artists greater than 500 and others
        /*Java 11
		Map<Boolean, List<String>> part = playList.stream()
				.collect(Collectors.partitioningBy(t -> {
					if(t.getDuration() == null) {
						return false;
					}
					int duration = Integer.valueOf(t.getDuration().replace(":", ""));
					return duration < 500;
				}, Collectors.flatMapping(t -> t.getArtists().stream(), 
						Collectors.toList())));

		
		part.forEach((k, v) -> System.out.println(k + " : " + v));
		*/
        //Java 9
        Map<Boolean, List<Track>> part2 = playList.stream()
                .collect(Collectors.partitioningBy(t -> {
                    if (t.getDuration() == null) {
                        return false;
                    }
                    int duration = Integer.valueOf(t.getDuration().replace(":", ""));
                    return duration < 500;
                }));

        Map<Boolean, List<String>> actlist = part2.entrySet().stream()
                .map(e -> {
                    List<String> al = e.getValue().stream().flatMap(t -> t.getArtists().stream())
                            .collect(Collectors.toList());
                    Map.Entry<Boolean, List<String>> en = new AbstractMap.SimpleImmutableEntry(e.getKey(),
                            al);
                    return en;

                }).collect(Collectors.toMap(en -> en.getKey(), en -> en.getValue()));

        actlist.forEach((k, v) -> {
            System.out.println(k + " : " + v);
        });

    }

    public void collectorsFlatmappingInJdk8() {
        TrackService ts = new TrackService();
        initService(ts);
        List<Track> playList = ts.getAllTracks();

        //Approach 1
        Map<Boolean, List<Track>> part2 = playList.stream()
                .collect(Collectors.partitioningBy(t -> {
                    if (t.getDuration() == null) {
                        return false;
                    }
                    int duration = Integer.valueOf(t.getDuration().replace(":", ""));
                    return duration < 500;
                }));

        Map<Boolean, List<String>> actlist = part2.entrySet().stream()
                .map(e -> {
                    List<String> al = e.getValue().stream().flatMap(t -> t.getArtists().stream())
                            .collect(Collectors.toList());
                    Map.Entry<Boolean, List<String>> en = new AbstractMap.SimpleImmutableEntry(e.getKey(),
                            al);
                    return en;

                }).collect(Collectors.toMap(en -> en.getKey(), en -> en.getValue()));
        //End approach 1

        //Approach 2 - Uses collectingAndThen to do both steps above in one step
        Map<Boolean, List<String>> part3 = playList.stream()
                .collect(Collectors.collectingAndThen(Collectors.partitioningBy(t -> {
                            if (t.getDuration() == null) {
                                return false;
                            }
                            int duration = Integer.valueOf(t.getDuration().replace(":", ""));
                            return duration < 500;
                        }),
                        (m) -> {
                            return m.entrySet().stream().map(e -> {
                                List<String> al = e.getValue().stream().flatMap(t -> t.getArtists().stream())
                                        .collect(Collectors.toList());
                                Map.Entry<Boolean, List<String>> en = new AbstractMap.SimpleImmutableEntry(e.getKey(),
                                        al);
                                return en;

                            }).collect(Collectors.toMap(en -> en.getKey(), en -> en.getValue()));
                        }));
        //End approach 2

        part3.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    public void initService(TrackService ts) {
        tracks().forEach(t -> ts.createTrack(t));
    }

    /* JDK 9+
    public List<Track> tracks() {
        List<Track> tracks = new ArrayList<>();
        tracks.add(new Track("The Shadow Of Your Smile", List.of("Big John Patton"), "Let 'em Roll", "06:15", "1965"));
        tracks.add(new Track("I'll Remember April", List.of("Jim Hall", "Ron Carter"), null, null, null));
        tracks.add(new Track("What's New", List.of("John Coltrane"), "Ballads", "03:47", null));
        tracks.add(new Track("Leave It to Me", List.of("Herb Ellis"), "Three Guitars in Bossa Nova Time", "03:13", "1963"));

        tracks.add(new Track("Have you met Miss Jones", List.of("George Van Eps"), "Pioneers of the Electric Guitar", "02:18",
                "2013"));

        tracks.add(new Track("My Funny Valentine", List.of("Johnny Smith"), "Moonlight in Vermont", "02:48", null));

        return tracks;
    }

     */
    //JDK 3
    public List<Track> tracks() {
        List<Track> tracks = new ArrayList<>();
        tracks.add(new Track("The Shadow Of Your Smile", Arrays.asList("Big John Patton"), "Let 'em Roll", "06:15", "1965"));
        tracks.add(new Track("I'll Remember April", Arrays.asList("Jim Hall", "Ron Carter"), null, null, null));
        tracks.add(new Track("What's New", Arrays.asList("John Coltrane"), "Ballads", "03:47", null));
        tracks.add(new Track("Leave It to Me", Arrays.asList("Herb Ellis"), "Three Guitars in Bossa Nova Time", "03:13", "1963"));

        tracks.add(new Track("Have you met Miss Jones", Arrays.asList("George Van Eps"), "Pioneers of the Electric Guitar", "02:18",
                "2013"));

        tracks.add(new Track("My Funny Valentine", Arrays.asList("Johnny Smith"), "Moonlight in Vermont", "02:48", null));

        return tracks;
    }
}
