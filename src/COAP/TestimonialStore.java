package COAP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Даниил
 */
public class TestimonialStore {

    private final List<IListener> listeners = new ArrayList<>();
    private static final Map<String, String> data = new HashMap<>();
    private static TestimonialStore instance = new TestimonialStore();

    public TestimonialStore() {
    }

    public static TestimonialStore getInstance() {
        return instance;
    }

    public void addListener(IListener listener) {
        listeners.add(listener);
    }

    public String getData() {
        String s = "";
        for (String key : data.keySet()) {
            s += getData(key) + "\n";
        }
        return s;
    }

    public String getData(String id) {
        String s = data.get(id);
        System.out.println(s);
        return s;
    }

    public void setData(String _id, String _data) {
        if (!data.containsKey(_id)) {
            for (IListener l : listeners) {
                l.onCreated(_id);
            }
        }
        data.put(_id, _data);
        for (IListener l : listeners) {
            l.onUpdated(_id, _data);
        }

    }
}
