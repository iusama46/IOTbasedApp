
package com.example.hassanproject.Helpers;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsageHistory {

    @SerializedName("Usage_History")
    @Expose
    private List<UsageHistoryDetails> usageHistory = null;
    @SerializedName("success")
    @Expose
    private String success;

    public List<UsageHistoryDetails> getUsageHistory() {
        return usageHistory;
    }

    public void setUsageHistory(List<UsageHistoryDetails> usageHistory) {
        this.usageHistory = usageHistory;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

}
