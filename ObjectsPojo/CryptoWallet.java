package com.example.testing.bitcoincoursenavigation.ObjectsPojo;



import com.example.testing.bitcoincoursenavigation.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class CryptoWallet implements Serializable {
    private String name;
    private String ShortName;
    private int CryptoImgid;
    private String cap;
    private String price;
    private float hourChange;
    private float dayChange;
    private float weekChange;
    public CryptoWallet(String name, String shortName, int CryptoImgid, String cap, String price, float hourChange, float dayChange, float weekChange) {
        this.cap = cap;
        this.CryptoImgid = CryptoImgid;
        this.name = name;
        this.price = price;
        this.ShortName = shortName;
        this.hourChange = hourChange;
        this.dayChange = dayChange;
        this.weekChange = weekChange;
    }
    public static List<CryptoWallet> GetCryptos() {
        List<CryptoWallet> list = new ArrayList<CryptoWallet>();
        list.add(new CryptoWallet("Bitcoin", "BTC", R.drawable.bitcoin, "", "", 0, 0, 0));
        list.add(new CryptoWallet("Ethereum", "ETH", R.drawable.ethereum, "", "", 0, 0, 0));
        list.add(new CryptoWallet("EOS", "EOS", R.drawable.eos, "", "", 0, 0, 0));
        list.add(new CryptoWallet("Cardano", "ADA", R.drawable.ada_cardano, "", "", 0, 0, 0));
        list.add(new CryptoWallet("Litecoin", "LTC", R.drawable.litecoin, "", "", 0, 0, 0));
        list.add(new CryptoWallet("IOTA", "IOTA", R.drawable.iota, "", "", 0, 0, 0));
        list.add(new CryptoWallet("NEM", "XEM", R.drawable.nem, "", "", 0, 0, 0));
        list.add(new CryptoWallet("Monero", "XMR", R.drawable.monero, "", "", 0, 0, 0));
        return list;
    }
    public static List<Integer> getThemes() {
        List<Integer> imageList = new ArrayList<Integer>();
        imageList.add(0, R.drawable.background_blue);
        imageList.add(1, R.drawable.background_platforms);
        imageList.add(2, R.drawable.background_red);
        imageList.add(3, R.drawable.background_waves);
        imageList.add(4, R.drawable.background_wolf);
        imageList.add(5, R.drawable.background_vokalize);
        imageList.add(6, R.drawable.background_chromosom);
        return imageList;
    }
    public String getName() {
        return name;
    }
    public void setPrice(String newPrice) {
        price = newPrice;
    }
    public String getShortName() {
        return ShortName;
    }
    public void setCap(String newCap) {
        cap = newCap;
    }
    public int getCryptoImgid() {
        return CryptoImgid;
    }
    public String getCap() {
        return cap;
    }
    public String getPrice() {
        return price;
    }

    public float getHourChange() {
        return hourChange;
    }

    public void setHourChange(float hourChange) {
        this.hourChange = hourChange;
    }

    public float getDayChange() {
        return dayChange;
    }

    public void setDayChange(float dayChange) {
        this.dayChange = dayChange;
    }

    public float getWeekChange() {
        return weekChange;
    }
    public static final Comparator<CryptoWallet> COMPARE_CAP_DOWN = new Comparator<CryptoWallet>() {
        @Override
        public int compare(CryptoWallet o1, CryptoWallet o2) {
            if (o1.getCap().length() > 1 && o2.getCap().length() > 1) {
                long FPrice = Long.parseLong(o1.getCap());
                long SPrice = Long.parseLong(o2.getCap());
                if (FPrice > SPrice) {
                    return 1;
                } else if (SPrice > FPrice) {
                    return -1;
                }
            }
            return 0;
        }
    };
    public static final Comparator<CryptoWallet> COMPARE_CAP_UP = new Comparator<CryptoWallet>() {
        @Override
        public int compare(CryptoWallet o1, CryptoWallet o2) {
            if (o1.getCap() != null && o2.getCap() != null) {
                if (o1.getCap().length() > 1 && o2.getCap().length() > 1) {
                    long FPrice = Long.parseLong(o1.getCap());
                    long SPrice = Long.parseLong(o2.getCap());
                    if (FPrice < SPrice) {
                        return 1;
                    } else if (FPrice > SPrice) {
                        return -1;
                    }
                }
            }
            return 0;

        }
    };
    public static final Comparator<CryptoWallet> COMPARE_PRICE_UP = new Comparator<CryptoWallet>() {
        @Override
        public int compare(CryptoWallet o1, CryptoWallet o2) {
            if(o1.getPrice().length() > 0 && o2.getPrice().length() > 0) {
                Float FPrice = Float.parseFloat(o1.getPrice());
                Float SPrice = Float.parseFloat(o2.getPrice());
                if (FPrice < SPrice) {
                    return 1;
                } else if (FPrice > SPrice) {
                    return -1;
                }
            }
            return 0;
        }
    };
    public static final Comparator<CryptoWallet> COMPARE_PRICE_DOWN = new Comparator<CryptoWallet>() {
        @Override
        public int compare(CryptoWallet o1, CryptoWallet o2) {
            if(o1.getPrice().length() > 0 && o2.getPrice().length() > 0) {
                Float FPrice = Float.parseFloat(o1.getPrice());
                Float SPrice = Float.parseFloat(o2.getPrice());
                if (FPrice > SPrice) {
                    return 1;
                } else if (FPrice < SPrice) {
                    return -1;
                }
            }
            return 0;
        }
    };
    public static final Comparator<CryptoWallet> COMPARE_HOUR_UP = new Comparator<CryptoWallet>() {
        @Override
        public int compare(CryptoWallet o1, CryptoWallet o2) {

                Float FPrice = o1.getHourChange();
                Float SPrice = o2.getHourChange();
                if (FPrice < SPrice) {
                    return 1;
                } else if (FPrice > SPrice) {
                    return -1;
                }

            return 0;
        }
    };
    public static final Comparator<CryptoWallet> COMPARE_HOUR_DOWN = new Comparator<CryptoWallet>() {
        @Override
        public int compare(CryptoWallet o1, CryptoWallet o2) {

                Float FPrice = o1.getHourChange();
                Float SPrice = o2.getHourChange();
                if (FPrice > SPrice) {
                    return 1;
                } else if (FPrice < SPrice) {
                    return -1;
                }

            return 0;
        }
    };
    public static final Comparator<CryptoWallet> COMPARE_DAY_UP = new Comparator<CryptoWallet>() {
        @Override
        public int compare(CryptoWallet o1, CryptoWallet o2) {

                Float FPrice = o1.getDayChange();
                Float SPrice = o2.getDayChange();
                if (FPrice < SPrice) {
                    return 1;
                } else if (FPrice > SPrice) {
                    return -1;
                }

            return 0;
        }
    };
    public static final Comparator<CryptoWallet> COMPARE_DAY_DOWN = new Comparator<CryptoWallet>() {
        @Override
        public int compare(CryptoWallet o1, CryptoWallet o2) {

                Float FPrice = o1.getDayChange();
                Float SPrice = o2.getDayChange();
                if (FPrice > SPrice) {
                    return 1;
                } else if (FPrice < SPrice) {
                    return -1;
                }

            return 0;
        }
    };
    public static final Comparator<CryptoWallet> COMPARE_WEEK_UP = new Comparator<CryptoWallet>() {
        @Override
        public int compare(CryptoWallet o1, CryptoWallet o2) {

                Float FPrice = o1.getWeekChange();
                Float SPrice = o2.getWeekChange();
                if (FPrice < SPrice) {
                    return 1;
                } else if (FPrice > SPrice) {
                    return -1;
                }

            return 0;
        }
    };

    public static final Comparator<CryptoWallet> COMPARE_WEEK_DOWN = new Comparator<CryptoWallet>() {
        @Override
        public int compare(CryptoWallet o1, CryptoWallet o2) {

            Float FPrice = o1.getWeekChange();
            Float SPrice = o2.getWeekChange();
            if (FPrice > SPrice) {
                return 1;
            } else if (FPrice < SPrice) {
                return -1;
            }

            return 0;
        }
    };
    public void setWeekChange(float weekChange) {
        this.weekChange = weekChange;
    }
}
