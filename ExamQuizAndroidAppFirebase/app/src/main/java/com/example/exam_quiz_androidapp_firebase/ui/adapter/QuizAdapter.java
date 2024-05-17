package com.example.exam_quiz_androidapp_firebase.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.exam_quiz_androidapp_firebase.R;
import com.example.exam_quiz_androidapp_firebase.data.model.QuizModel;
import com.example.exam_quiz_androidapp_firebase.databinding.CardDesignBinding;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.CardDesignHolder> {

    private Context mContext;
    private List<QuizModel> quizModelList;
    private OnItemClicked itemClicked;

    public QuizAdapter(Context mContext, List<QuizModel> quizModelList, OnItemClicked itemClicked) {
        this.mContext = mContext;
        this.quizModelList = quizModelList;
        this.itemClicked = itemClicked;
    }

    public void setQuizModelData(List<QuizModel> quizModelData) {
        this.quizModelList = quizModelData;
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

        cardDesignBinding.textViewQuizTitle.setText(quizModel.getQuizname());
        cardDesignBinding.textViewQuizDescription.setText(quizModel.getDescription());
        cardDesignBinding.textViewListLevel.setText(quizModel.getLevel());

        Glide.with(mContext)
                .load(quizModel.getImage())
                .placeholder(R.drawable.generalknowladge)
                .centerCrop()
                .into(cardDesignBinding.imageView);

        cardDesignBinding.buttonStartQuiz.setOnClickListener(view ->{
            itemClicked.somethingClicked(holder.getAdapterPosition());
        });

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
    public interface OnItemClicked {
        void somethingClicked(int position);
    }
}

