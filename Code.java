// Shortest Path Using Atmost One Curved Edge

class Code {
    static class Pair {
        int v, w;
        Pair(int v, int w) { this.v = v; this.w = w; }
    }

    public int shortestPath(int V, int a, int b, int[][] edges) {
        List<List<Pair>> g = new ArrayList<>();
        for (int i = 0; i < V; i++) g.add(new ArrayList<>());

        for (int[] e : edges) {
            int u = e[0], v = e[1], w1 = e[2];
            g.get(u).add(new Pair(v, w1));
            g.get(v).add(new Pair(u, w1));
        }

        long[] distA = dijkstra(V, a, g);
        long[] distB = dijkstra(V, b, g);

        long ans = distA[b];
        for (int[] e : edges) {
            int u = e[0], v = e[1], w2 = e[3];
            if (distA[u] < Long.MAX_VALUE && distB[v] < Long.MAX_VALUE)
                ans = Math.min(ans, distA[u] + w2 + distB[v]);
            if (distA[v] < Long.MAX_VALUE && distB[u] < Long.MAX_VALUE)
                ans = Math.min(ans, distA[v] + w2 + distB[u]);
        }

        return ans >= Long.MAX_VALUE ? -1 : (int) ans;
    }

    long[] dijkstra(int V, int src, List<List<Pair>> g) {
        long[] dist = new long[V];
        Arrays.fill(dist, Long.MAX_VALUE);
        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> a.w - b.w);
        pq.add(new Pair(src, 0));
        dist[src] = 0;

        while (!pq.isEmpty()) {
            Pair cur = pq.poll();
            int u = cur.v;
            if (cur.w > dist[u]) continue;
            for (Pair p : g.get(u)) {
                if (dist[p.v] > dist[u] + p.w) {
                    dist[p.v] = dist[u] + p.w;
                    pq.add(new Pair(p.v, (int) dist[p.v]));
                }
            }
        }
        return dist;
    }
                               }
