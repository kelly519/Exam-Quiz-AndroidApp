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
import com.example.exam_quiz_androidapp_firebase.ui.fragment.appfragment.ListFragment;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.CardDesignHolder> {

    private Context mContext;
    private List<QuizModel> quizModelList;
    private OnItemClicked itemClicked;

    public QuizAdapter(OnItemClicked itemClicked) {
        this.itemClicked = itemClicked;
    }
    public void setQuizModelData(List<QuizModel> quizModelData) {
        this.quizModelList = quizModelData;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CardDesignHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        CardDesignBinding binding = CardDesignBinding.inflate(LayoutInflater.from(context), parent, false);
        return new CardDesignHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CardDesignHolder holder, int position) {
        QuizModel quizModel = quizModelList.get(position);
        CardDesignBinding cardDesignBinding = holder.designBinding;

        cardDesignBinding.textViewQuizTitle.setText(quizModel.getQuizname());
        cardDesignBinding.textViewQuizDescription.setText(quizModel.getDescription());
        cardDesignBinding.textViewListLevel.setText(quizModel.getLevel());

        Glide.with(cardDesignBinding.imageView.getContext())
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
        return quizModelList != null ? quizModelList.size() : 0; // bak
    }

    public static class CardDesignHolder extends RecyclerView.ViewHolder {
        private final CardDesignBinding designBinding;
        public CardDesignHolder(CardDesignBinding designBinding) {
            super(designBinding.getRoot());
            this.designBinding = designBinding;
        }
    }
    public interface OnItemClicked {
        void somethingClicked(int position);
    }
}

