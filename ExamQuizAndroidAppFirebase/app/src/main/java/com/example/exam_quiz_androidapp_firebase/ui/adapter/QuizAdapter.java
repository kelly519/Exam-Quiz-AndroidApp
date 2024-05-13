package com.example.exam_quiz_androidapp_firebase.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exam_quiz_androidapp_firebase.data.model.QuizModel;
import com.example.exam_quiz_androidapp_firebase.databinding.CardDesignBinding;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.CardDesignHolder> {

    private Context mContext;
    private List<QuizModel> quizModelList;

    public QuizAdapter(Context mContext, List<QuizModel> quizModelList) {
        this.mContext = mContext;
        this.quizModelList = quizModelList;
    }

    @NonNull
    @Override
    public CardDesignHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardDesignBinding binding = CardDesignBinding.inflate(LayoutInflater.from(mContext),parent,false);
        return new CardDesignHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CardDesignHolder holder, int position) {
        QuizModel quizModel = quizModelList.get(position);
        CardDesignBinding cardDesignBinding = holder.designBinding;

        cardDesignBinding.

    }

    @Override
    public int getItemCount() {
        return quizModelList.size();
    }

    public class CardDesignHolder extends RecyclerView.ViewHolder{
        private CardDesignBinding designBinding;

        public CardDesignHolder(CardDesignBinding designBinding) {
            super(designBinding.getRoot());
            this.designBinding = designBinding;
        }
    }
}
