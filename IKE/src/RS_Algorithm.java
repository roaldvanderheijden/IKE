import java.util.Collection;

//import de.umass.lastfm.Track;

public interface RS_Algorithm {

	public abstract String getRecommendations(Collection<RS_Track> tracks);

}