package yee.pltision.randomadditions.event;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Random;

/*
    什么? 你还在用mojang的O(n)复杂度的WeightedRandomList? 来看看我的O(lg(n))算法吧!
    (老项目cv过来的代码, 这条注释也保留吧喵)
 */
public class WeightedPool<T> {
    record RandomBlock<T>(T item,int from,int to){}
    ArrayList<RandomBlock<T>> pool;
    int sum=0;

    public WeightedPool(){
        pool=new ArrayList<>();
    }

    @SafeVarargs
    public WeightedPool(WeightedItem<T>...items){
        this();
        for(WeightedItem<T> item:items) add(item);
    }

    public int sum() {
        return sum;
    }

    public void add(T item, int weight){
        pool.add(new RandomBlock<>(item, sum, sum += weight));
    }
    public void add(WeightedItem<T> item){
        add(item.item, item.weight);
    }

    /**
     * @return 当池为空时返回null
     */
    public T random(Int2IntFunction random){
        if(sum==0) return null;
        return getItem(random.applyAsInt(sum));
    }

    public @Nullable T getItem(int at){
        if(pool.isEmpty()) return null;
        int max= pool.size(), min=0,ans=-1;
        while(min<max){
            int mid=(max+min)>>1;
            if(pool.get(mid).from<=at){
                ans=mid;
                min=mid+1;
            }
            else max=mid;
        }
        if(ans==-1&&pool.get(ans).to<=at) return null;
        return pool.get(ans).item;
    }

    public record WeightedItem<T>(T item, int weight){
        @Contract("_, _ -> new")
        public static <T> @NotNull WeightedItem<T> wi(T item, int weight){
            return new WeightedItem<>(item,weight);
        }
    }
}
